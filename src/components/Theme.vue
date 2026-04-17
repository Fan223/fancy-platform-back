<template>
  <div :style="{ height: props.size, width: props.size }" cursor-pointer>
    <div
      class="i-ic:twotone-contrast"
      size-full
      v-if="'os' === theme"
      @click="changeTheme('light')"
    />
    <div
      class="i-line-md:moon-filled-to-sunny-filled-loop-transition"
      size-full
      v-if="'light' === theme"
      @click="changeTheme('dark')"
    />
    <div
      class="i-line-md:sunny-filled-loop-to-moon-filled-loop-transition"
      size-full
      v-if="'dark' === theme"
      @click="changeTheme('light')"
    />
  </div>
</template>

<script setup lang="ts">
import theme from "@/assets/ts/theme";
import { type Theme } from "@/assets/ts/theme";

const props = withDefaults(defineProps<{ size?: string }>(), {
  size: "2rem",
});

function changeTheme(val: Theme) {
  theme.value = val;
}
</script>
