<template>
  <div class="starry-container" :class="{ 'is-light': !isDark }">
    <!--
      星星三层：暗色主题专属
      用 v-if 完全卸载，切换到亮色时不占 GPU 资源
      星星没有"淡入"需求，直接消失即可，所以 v-if 没问题
    -->
    <div class="starry starry1" v-show="isDark" />
    <div class="starry starry2" v-show="isDark" />
    <div class="starry starry3" v-show="isDark" />

    <!--
      光晕三层：始终保留在 DOM 中，用 v-show 控制
      原因：光晕的显隐依赖 CSS `transition: opacity 0.9s`
      v-if 会销毁节点，节点从"不存在"变"存在"时浏览器跳过 transition
      v-show 只切换 display，节点始终在 DOM，opacity 过渡正常触发
      实际的 opacity: 0→1 由 .is-light & { opacity: 1 } 驱动
    -->
    <div class="light light1" />
    <div class="light light2" />
    <div class="light light3" />
    <!--
      樱花 canvas：亮色主题专属，v-if 挂载/卸载
      卸载时 watch(sakuraCanvas) 收到 null，自动调用 stopSakura() 取消 rAF
      这样暗色模式下不会有 canvas 动画在后台空跑
    -->
    <canvas v-if="!isDark" ref="sakuraCanvas" class="sakura-canvas" />
  </div>
</template>

<script setup lang="ts">
import { debounce } from "./assets/ts/debounce";
import theme from "./assets/ts/theme";
import { useGlobalStore } from "@/pinia";

const { screenWidth } = storeToRefs(useGlobalStore());

const isDark = computed(() => theme.value === "dark");
// 鼠标视差
// 将鼠标横向位置映射为 CSS 变量，星星动画读取该变量做横向漂移
// 用 requestAnimationFrame 节流，避免每次 mousemove 都强制回流
let mouseMoveHandler: (e: MouseEvent) => void;
onMounted(() => {
  mouseMoveHandler = (e: MouseEvent) => {
    requestAnimationFrame(() => {
      // 将鼠标位置归一化到 [-0.5, 0.5]，乘以幅度系数得到偏移像素
      const x = (e.clientX / window.innerWidth - 0.5) * 10;
      document.documentElement.style.setProperty("--mouse-x", `${x}px`);
    });
  };
  window.addEventListener("mousemove", mouseMoveHandler);
});

onBeforeUnmount(() => {
  window.removeEventListener("mousemove", mouseMoveHandler);
  stopSakura();
});

// 樱花 Canvas
interface Petal {
  x: number; // 当前横坐标
  y: number; // 当前纵坐标
  r: number; // 花瓣短半轴（长半轴 = r * 1.65）
  color: string; // rgba 颜色字符串
  speed: number; // 每帧下落速度（px）
  swing: number; // 横向摆动幅度
  swingOffset: number; // 摆动相位（随时间累加）
  swingSpeed: number; // 摆动频率
  rot: number; // 当前旋转角度（rad）
  rotSpeed: number; // 每帧旋转增量
}

const COLORS = [
  "rgba(255,182,193,0.75)",
  "rgba(255,160,180,0.6)",
  "rgba(255,200,210,0.65)",
  "rgba(240,150,170,0.5)",
  "rgba(255,190,200,0.55)",
];
const PETAL_COUNT = 35;

let petals: Petal[] = [];
let rafId: number | null = null;
// 生成单片花瓣的初始状态
// startY 未传时随机分布在画布内，传 -10 表示从顶部外侧飘入
function mkPetal(canvas: HTMLCanvasElement, startY?: number): Petal {
  return {
    x: Math.random() * canvas.width,
    y: startY ?? Math.random() * canvas.height,
    r: 3 + Math.random() * 3.5,
    color: COLORS[Math.floor(Math.random() * COLORS.length)] as string,
    speed: 0.5 + Math.random() * 0.6,
    swing: 0.8 + Math.random() * 1.0,
    swingOffset: Math.random() * Math.PI * 2,
    swingSpeed: 0.008 + Math.random() * 0.012,
    rot: Math.random() * Math.PI * 2,
    rotSpeed: (Math.random() - 0.5) * 0.035,
  };
}

// 用 canvas 2D API 画单片椭圆花瓣
function drawPetal(ctx: CanvasRenderingContext2D, p: Petal) {
  ctx.save();
  ctx.translate(p.x, p.y); // 移动原点到花瓣位置
  ctx.rotate(p.rot); // 旋转
  ctx.beginPath();
  ctx.fillStyle = p.color;
  // 椭圆：宽 r，高 r*1.65，模拟樱花花瓣细长形态
  ctx.ellipse(0, 0, p.r, p.r * 1.65, 0, 0, Math.PI * 2);
  ctx.fill();
  ctx.restore();
}

function startSakura(canvas: HTMLCanvasElement) {
  const ctx = canvas.getContext("2d")!;

  // canvas 的 width/height 属性必须手动设置为元素实际尺寸
  // 否则默认 300×150，坐标系与显示尺寸不一致导致花瓣位置错误
  const resize = () => {
    canvas.width = canvas.offsetWidth;
    canvas.height = canvas.offsetHeight;
  };
  resize();

  // 初始化时让花瓣随机分布在整个画面，而不是全部从顶部飘入
  petals = Array.from({ length: PETAL_COUNT }, () => mkPetal(canvas));

  const loop = () => {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    petals.forEach((p) => {
      // 用 sin 函数模拟左右飘动，swingOffset 随时间线性增加形成周期摆动
      p.swingOffset += p.swingSpeed;
      p.x += Math.sin(p.swingOffset) * p.swing;
      p.y += p.speed; // 匀速下落
      p.rot += p.rotSpeed; // 持续旋转

      // 超出底部时重置到顶部外侧，实现循环飘落
      if (p.y > canvas.height + 10) {
        Object.assign(p, mkPetal(canvas, -10));
      }

      drawPetal(ctx, p);
    });

    rafId = requestAnimationFrame(loop); // 下一帧继续
  };
  loop();
}

function stopSakura() {
  // 取消 rAF，避免 canvas 卸载后动画循环继续在后台执行
  if (rafId !== null) {
    cancelAnimationFrame(rafId);
    rafId = null;
  }
  petals = [];
}

const sakuraCanvas = ref<HTMLCanvasElement | null>(null);
// Canvas 挂载后启动，切回暗色时停止
watch(sakuraCanvas, (canvas) => {
  if (canvas) {
    // nextTick 确保 DOM 完全挂载、offsetWidth 有值后再初始化
    nextTick(() => startSakura(canvas));
  } else {
    stopSakura();
  }
});
const handleResize = debounce(() => {
  const canvas = sakuraCanvas.value;
  if (!canvas) return;

  canvas.width = canvas.offsetWidth;
  canvas.height = canvas.offsetHeight;

  // 花瓣坐标按新宽度重新随机分布，避免花瓣堆在旧尺寸区域外
  petals.forEach((p) => {
    p.x = Math.random() * canvas.width;
  });
}, 200);

watch(screenWidth, handleResize);
</script>

<style scoped lang="scss">
@use "sass:math";
@use "sass:string";

@function generate-shadows($n, $color) {
  $shadows: "#{math.random(100)}vw #{math.random(100)}vh #{$color}";

  @for $i from 2 through $n {
    $shadows: "#{$shadows}, #{math.random(100)}vw #{math.random(100)}vh #{$color}";
  }

  @return string.unquote($shadows);
}

.starry-container {
  position: fixed;
  inset: 0;
  overflow: hidden;
  pointer-events: none;
  z-index: -999;
  background: var(--background-primary);

  // 先让背景色渐变，内容层（星星/光晕）再跟上，视觉上像天色渐变
  transition: background 1.2s cubic-bezier(0.4, 0, 0.2, 1);

  // 银河雾：两个半透明径向渐变叠加，增加星空纵深感
  // 用 ::before 伪元素单独控制，切换主题时可以独立淡出
  &::before {
    content: "";
    position: fixed;
    inset: 0;
    background:
      radial-gradient(ellipse at 30% 20%, rgb(84 51 255 / 8%), transparent 60%),
      radial-gradient(ellipse at 70% 80%, rgb(251 146 60 / 9%), transparent 60%);
    pointer-events: none;
    opacity: 1;
    transition: opacity 0.8s ease;
  }

  // 亮色主题：切换背景色 + 银河雾淡出
  &.is-light {
    &::before {
      opacity: 0;
    }
  }
}

// ─── 星星基类 ───────────────────────────────────────────────
// 星星本身尺寸极小（1~2.5px），视觉效果完全来自 box-shadow
// box-shadow 会跟随元素位置，所以 div 的位置并不重要
// ::after 伪元素复制一份相同的 box-shadow，偏移 100vh（一屏高度）
// 配合 move-up 动画：当主元素移出顶部时，::after 正好从底部进入
// 两者无缝衔接，形成无限循环的星星上移效果
.starry {
  border-radius: 50%;
  transition: opacity 0.6s ease;

  .is-light & {
    opacity: 0;
  }

  &::after {
    content: "";
    position: fixed;
    top: 100vh;
    width: inherit;
    height: inherit;
    border-radius: inherit;
    box-shadow: inherit;
  }
}

.starry1 {
  will-change: transform; // 提示浏览器此元素会频繁变换，提前创建合成层
  width: 1px;
  height: 1px;
  box-shadow: generate-shadows(300, #d6e4ff);
  animation: move-up 25s linear infinite;
  opacity: 0.8;

  .is-light & {
    opacity: 0;
  }
}

.starry2 {
  will-change: transform, opacity;
  width: 1.5px;
  height: 1.5px;
  box-shadow: generate-shadows(100, #fff);
  animation:
    move-up 50s linear infinite,
    twinkle-md 6s ease-in-out infinite alternate;

  // 随机起始相位，避免所有星同时闪烁
  animation-delay: calc(-1s * #{math.random(60)});
}

.starry3 {
  will-change: transform, opacity;
  width: 2.5px;
  height: 2.5px;

  // 混合两种颜色：暖金色为主，少量纯白，模拟不同色温的亮星
  box-shadow: generate-shadows(20, #ffe9a3), generate-shadows(10, #fff);
  animation:
    move-up 80s linear infinite,
    twinkle-lg 9s ease-in-out infinite alternate;
  animation-delay: calc(-1s * #{math.random(60)});
}

// ─── 星星动画 ───────────────────────────────────────────────
// 星星从当前位置向上移动一整屏
// --mouse-x 由 JS 实时写入，随鼠标横向移动微调，形成视差漂移感
@keyframes move-up {
  from {
    transform: translate3d(var(--mouse-x, 0), 0, 0);
  }

  to {
    transform: translate3d(calc(var(--mouse-x, 0px) - 2vw), -100vh, 0);
  }
}

// 中等星：opacity + scale 组合，scale 让星点有呼吸感
// 注意：scale 和 move-up 的 translate 在同一个 transform 上会冲突
// 这里 twinkle 只在 starry2/3 上，move-up 和 twinkle 分别作用于不同属性时没问题
// 但如果两个 @keyframes 都用 transform 会互相覆盖——这是一个潜在问题
@keyframes twinkle-md {
  0% {
    opacity: 0.3;
    transform: scale(0.9);
  }

  50% {
    opacity: 1;
    transform: scale(1.1);
  }

  100% {
    opacity: 0.6;
    transform: scale(1);
  }
}

// 强闪烁
@keyframes twinkle-lg {
  0% {
    opacity: 0.4;
    transform: scale(0.85);
  }

  50% {
    opacity: 1;
    transform: scale(1.25);
  }

  100% {
    opacity: 0.7;
    transform: scale(1.05);
  }
}

// ─── 日间光晕 ───────────────────────────────────────────────
// 光晕始终在 DOM 中（非 v-if），靠 opacity transition 实现平滑显隐
// 默认 opacity: 0 隐藏，.is-light & 时变为 1
// transition-delay: 0.3s 让背景色先走一步，视觉上更自然
.light {
  position: fixed;
  border-radius: 50%;
  pointer-events: none;
  will-change: transform;
  opacity: 0;
  transition: opacity 0.9s ease 0.3s; // delay 0.3s：等背景色先开始变化
  filter: blur(60px); // 大范围模糊，形成柔和光晕而非实心圆

  .is-light & {
    opacity: 1;
  }
}

// 左上暖黄：模拟阳光来源
.light1 {
  width: 55vw;
  height: 55vw;
  top: -20%;
  left: -8%;
  background: radial-gradient(
    circle at 40% 40%,
    rgb(255 195 80 / 55%),
    rgb(255 210 100 / 20%) 45%,
    transparent 70%
  );
  animation: light-drift 22s ease-in-out infinite alternate;
}

// 右下冷蓝：模拟天空漫反射
.light2 {
  width: 40vw;
  height: 40vw;
  bottom: -15%;
  right: 2%;
  background: radial-gradient(
    circle at 55% 55%,
    rgb(100 170 255 / 50%),
    rgb(130 185 255 / 18%) 45%,
    transparent 70%
  );
  animation: light-drift 28s ease-in-out infinite alternate-reverse;
}

// 中部淡绿：环境光补充
.light3 {
  width: 30vw;
  height: 30vw;
  top: 35%;
  left: 50%;
  background: radial-gradient(
    circle at 50% 50%,
    rgb(180 230 190 / 42%),
    rgb(160 220 175 / 15%) 45%,
    transparent 70%
  );
  animation: light-drift 19s ease-in-out infinite alternate;
  animation-delay: -6s;
}

// 光晕缓慢漂移：translate + scale 组合，模拟大气光的流动感
@keyframes light-drift {
  0% {
    transform: translate(0, 0) scale(1);
  }

  50% {
    transform: translate(3vw, -4vh) scale(1.08);
  }

  100% {
    transform: translate(-2vw, 3vh) scale(0.95);
  }
}

// 樱花 Canvas
.sakura-canvas {
  position: fixed;
  inset: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  opacity: 0;
  transition: opacity 0.9s ease 0.4s; // 比光晕多延迟 0.1s，让光晕先出现，樱花再飘入

  .is-light & {
    opacity: 1;
  }
}
</style>
