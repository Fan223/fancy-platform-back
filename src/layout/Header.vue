<template>
  <header
    class="header"
    :class="{ active: scrollPercent > 0 }"
    sticky
    left-0
    top-0
    flex="~ justify-between items-center"
    p="x-8 y-4"
    c-white
    z-999
  >
    <Logo />

    <div @click="$router.push('/blog')">博客</div>
    <div @click="$router.push('/tool')">在线工具</div>
    <div @click="$router.push('/dict')">词典</div>
    <div @click="$router.push('/nav')">资源导航</div>

    <Theme />
  </header>
</template>

<script lang="ts" setup>
import { useGlobalStore } from "@/pinia";

const { scrollPercent } = storeToRefs(useGlobalStore());
</script>

<style lang="scss" scoped>
.header {
  transition: var(--theme-transition);

  &.active {
    background-color: var(--background-backdrop);
    backdrop-filter: blur(0.5rem);
    color: var(--text-bright);
    padding: 0.5rem 2rem;
  }
}
</style>
