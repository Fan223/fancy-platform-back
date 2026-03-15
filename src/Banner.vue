<template>
  <div flex="~ justify-center" absolute top-0 h-fit w-full z--999>
    <div class="banner-dark" absolute h-100 blur-72 z--999 />
    <div style="background: var(--banner-cover)" h-75 w-full mt-75 />
  </div>
</template>

<script setup lang="ts">
import theme from "@/assets/ts/theme";

onMounted(() => {
  let banner = document.getElementsByClassName("banner-dark")[0] as HTMLElement;

  watchEffect(() => {
    if (
      "light" === theme.value ||
      ("os" === theme.value &&
        matchMedia("(prefers-color-scheme: light)").matches)
    ) {
      banner.classList.add("banner-light");
    } else {
      banner.classList.remove("banner-light");
    }
  });
});
</script>

<style scoped lang="scss">
.banner-dark {
  width: 85%;
  background: var(--main-gradient);
  transition: var(--theme-transition);
}

.banner-light {
  height: 37.5rem;
  width: 100%;
  filter: blur(0);
}
</style>
