<template>
  <Header />

  <button @click="fixed = !fixed">click</button>

  <div
    grid
    gap-6
    m-auto
    :style="{
      gridTemplateColumns: layoutGridTemplate,
      border: '1px solid red',
      maxWidth: fixed ? '1280px' : 'none',
    }"
  >
    <aside
      v-if="hasLeft"
      :style="{ height: 'calc(100vh - 6rem)', border: '1px solid pink' }"
      sticky
      top-20
      overflow-y-auto
    >
      <RouterView name="left" />
    </aside>

    <div
      :style="{
        marginLeft: hasLeft ? '0' : '1.5rem',
        marginRight: hasRight ? '0' : '1.5rem',
      }"
    >
      <main
        grid
        gap-6
        :style="{
          gridTemplateColumns: contentdGridTemplate,
          border: '1px solid white',
        }"
      >
        <div class="content" style="border: 1px solid orange">
          <RouterView />
        </div>

        <!-- <div
          :style="{
            paddingRight: fixed
              ? screenWidth < 1304
                ? '1.5rem'
                : '0'
              : '1.5rem',
          }"
        > -->
        <aside
          v-if="hasRight"
          :style="{
            height: 'calc(100vh - 6rem)',
            scrollbarWidth: 'none',
            border: '1px solid blue',
            // boxSizing: 'content-box',
            marginRight: fixed
              ? screenWidth < 1304
                ? '1.5rem'
                : '0'
              : '1.5rem',
          }"
          sticky
          top-20
          overflow-y-auto
        >
          <RouterView name="right" />
        </aside>
        <!-- </div> -->
      </main>

      <Footer />
    </div>
  </div>

  <BackTop />
</template>

<script lang="ts" setup>
import Footer from "./Footer.vue";
import Header from "./Header.vue";
import { useGlobalStore } from "@/pinia";

const { screenWidth } = storeToRefs(useGlobalStore());

const route = useRoute();
// 取最后一个即当前子路由
const currentMatched = computed(() => route.matched.at(-1));
const hasLeft = computed(() => !!currentMatched.value?.components?.left);
const hasRight = computed(() => !!currentMatched.value?.components?.right);

const layoutGridTemplate = computed(() => {
  if (hasLeft.value) {
    if (screenWidth.value <= 768) {
      return "0 1fr";
    }
    return "clamp(240px, 20%, 270px) 1fr";
  }
  return "1fr";
});

const contentdGridTemplate = computed(() => {
  if (hasRight.value) {
    if (screenWidth.value <= 992) {
      return "1fr 0";
    }
    // return "1fr clamp(240px, 20%, 270px)";
    return "1fr 240px";
  }
  return "1fr";
});

const fixed = ref(false);
</script>

<style lang="scss" scoped>
// .layout {
//   --sidebar-left-width: 240px;
//   --sidebar-right-width: 240px;
//   --content-max-width: 1100px;
//   --layout-max-width: 1340px;

//   display: grid;

//   // gap: 1.5rem;

//   // margin: 0 auto;
// }

// .sidebar-left {
//   position: sticky;
//   top: 5rem;
//   height: calc(100vh - 6rem);
//   overflow-y: auto;
// }

// .content {
//   width: 100%;
// }
</style>
