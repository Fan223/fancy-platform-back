<template>
  <div
    :class="['article-container', `article-container--${props.layout}`]"
    :style="{ '--article-column': props.column }"
  >
    <template v-for="article in articles" :key="article.id">
      <RouterLink
        :to="`/article/preview/${article.id}`"
        target="_blank"
        rel="noopener noreferrer"
      >
        <div :class="['article', `article--${defaultLayout}`]">
          <!-- 缩略图 -->
          <div
            :class="[
              'article__thumbnail',
              { 'article__thumbnail--vertical': 'vertical' === defaultLayout },
            ]"
            :style="{ height: defaultHeight }"
          >
            <!-- 图片 -->
            <img :src="article.cover" object-cover rd-4 />
            <!-- 颜色混合层 -->
            <div
              style="background: var(--main-gradient)"
              rd-4
              op-50
              mix-blend-screen
              z-1
            />
            <!-- 遮盖层 -->
            <div
              :style="{
                background: `var(--article-cover-${defaultLayout})`,
              }"
              z-2
            />
          </div>

          <!-- 内容 -->
          <div flex="~ col grow-1" mx-6>
            <div
              :style="{
                marginTop: 'horizontal' === defaultLayout ? '0' : 'auto',
              }"
            >
              <h1
                :style="{
                  color: 'var(--text-bright)',
                  fontSize:
                    'horizontal' === defaultLayout ? '1.625rem' : '2.5rem',
                }"
              >
                {{ article.title }}
              </h1>
              <p
                :style="{
                  fontSize:
                    'horizontal' === defaultLayout ? '1.125rem' : '1.25rem',
                }"
                my-4
              >
                {{ article.content }}
              </p>
            </div>

            <div
              style="color: var(--text-dim)"
              flex="~ items-center"
              m="t-auto b-4"
            >
              <Logo height="1.5rem" width="1.25rem" />
              <div ml-4>最后修改于 {{ article.updateTime }}</div>
            </div>
          </div>
        </div>
      </RouterLink>
    </template>
  </div>
</template>

<script setup lang="ts">
import type { Article } from "./article";
import { useGlobalStore } from "@/pinia";

interface Props {
  articles: Article[];
  layout?: "horizontal" | "vertical";
  height?: string;
  column?: number;
}

const props = withDefaults(defineProps<Props>(), {
  layout: "horizontal",
  column: 2,
});

const { screenWidth } = storeToRefs(useGlobalStore());

const defaultLayout = computed(() => {
  return screenWidth.value < 992 ? "horizontal" : props.layout;
});

const defaultHeight = computed(
  () =>
    props.height ?? ("horizontal" === defaultLayout.value ? "15rem" : "30rem"),
);
</script>

<style scoped lang="scss">
.article-container {
  display: grid;
  gap: 2rem;
  margin: 2rem;

  &--horizontal {
    grid-template-columns: repeat(var(--article-column), minmax(0, 1fr));
  }

  &--vertical {
    grid-template-columns: 1fr;
  }
}

@media (width <= 992px) {
  .article-container--horizontal {
    grid-template-columns: repeat(
      calc(var(--article-column) - 1),
      minmax(0, 1fr)
    );
  }
}

@media (width <= 768px) {
  .article-container--horizontal {
    grid-template-columns: 1fr;
  }
}

.article {
  display: flex;
  height: 100%;
  background-color: var(--background-secondary);
  border-radius: 1rem;

  &--horizontal {
    flex-direction: column;
  }

  &--vertical {
    align-items: stretch;
  }

  &__thumbnail {
    display: grid;

    > * {
      grid-area: 1/1;
      height: inherit;
      width: 100%;
    }

    &--vertical {
      flex: 0 0 60%;
    }
  }
}
</style>
