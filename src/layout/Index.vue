<template>
  <Header />

  <main :style="{ gridTemplateColumns: gridTemplate }" class="layout">
    <aside v-if="hasLeft" style="border: 1px solid red">
      <RouterView name="left" />
    </aside>

    <section class="content" style="border: 1px solid blue">
      <RouterView />
    </section>

    <aside v-if="hasRight" style="border: 1px solid black">
      <RouterView name="right" />
    </aside>
  </main>
</template>

<script lang="ts" setup>
import Header from "./Header.vue";

const route = useRoute();

// route.matched 是当前匹配的路由层级数组，取最后一个即当前子路由
const currentMatched = computed(() => route.matched.at(-1));

const hasLeft = computed(() => !!currentMatched.value?.components?.left);
const hasRight = computed(() => !!currentMatched.value?.components?.right);

console.log(hasLeft.value);
console.log(hasRight.value);

const gridTemplate = computed(() => {
  if (hasLeft.value && hasRight.value) return "240px minmax(0,1fr) 300px";

  if (hasLeft.value) return "240px minmax(0,1fr)";

  if (hasRight.value) return "minmax(0,1fr) 300px";

  return "minmax(0,1fr)";
});
</script>

<style lang="scss" scoped>
.layout {
  display: grid;
  gap: 24px;
}
</style>
