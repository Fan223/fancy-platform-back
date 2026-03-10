<template>
  <header>
    <div @click="router.push('/blog')">博客</div>
    <div @click="router.push('/tool')">在线工具</div>
  </header>
</template>

<script lang="ts" setup>
import router from "@/router";
</script>

<style lang="scss" scoped></style>
