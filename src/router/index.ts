import {createRouter, createWebHistory, type RouteRecordRaw,} from "vue-router";

const routes: RouteRecordRaw[] = [
  {
    path: "/",
    name: "Layout",
    component: () => import("@/layout/Index.vue"),
    redirect: "/blog",
    children: [
      {
        path: "/blog",
        name: "Blog",
        components: {
          default: () => import("@/views/blog/Index.vue"),
          left: () => import("@/views/blog/BlogLeft.vue"),
          right: () => import("@/views/blog/BlogRight.vue"),
        },
      },
      {
        path: "/tool",
        name: "Tool",
        components: {
          default: () => import("@/views/tool/Index.vue"),
          left: () => import("@/views/tool/ToolLeft.vue"),
        },
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(_to, _from, savedPosition) {
    return savedPosition ? savedPosition : { left: 0, top: 0 };
  },
});

export default router;
