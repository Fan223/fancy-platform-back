<template>
  <div
    v-if="scrollPercent > 0"
    :style="{
      background: `conic-gradient(var(--text-accent) calc(${scrollPercent}%), var(--background-secondary) 0)`,
    }"
    @click="scrollToTop"
    fixed
    right-10
    bottom-10
    p-1
    rd="50%"
    cursor-pointer
    z-999
  >
    <div style="background-color: var(--background-primary)" p-1 rd="50%">
      <SizeBox
        class="i-line-md:upload-loop"
        style="color: var(--text-accent)"
        size="2.5rem"
        fill
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { useGlobalStore } from "@/pinia";

const { scrollPercent, screenWidth } = storeToRefs(useGlobalStore());

// 计算滚动百分比
function calcScrollPercent() {
  let { scrollHeight, clientHeight, scrollTop } = document.documentElement;
  // 当前滚动的高度 / (整个页面内容的高度 - 浏览器视口的可视区域高度)
  let scrolled = Math.ceil((scrollTop / (scrollHeight - clientHeight)) * 100);
  scrollPercent.value = Math.min(scrolled, 100);
}

// 处理滚动事件
function handleScroll() {
  requestAnimationFrame(calcScrollPercent);
}

// 处理屏幕尺寸变化事件
function handleScreenResize() {
  screenWidth.value = window.innerWidth;
  handleScroll();
}

function scrollToTop() {
  window.scrollTo({ top: 0, behavior: "smooth" });
}

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
  window.addEventListener("resize", handleScreenResize);
});
onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
  window.removeEventListener("resize", handleScreenResize);
});
</script>
