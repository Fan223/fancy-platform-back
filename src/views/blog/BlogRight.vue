<template>
  <div>
    <b>推荐文章</b>
    <FancyList :items="articles">
      <template #default="{ item }">
        <div class="flex items-center justify-between">
          <span>{{ item.title }}</span>
        </div>
      </template>

      <template #item="item">
        <div class="text-gray-300">{{ item.title }}</div>
      </template>
    </FancyList>
  </div>
</template>

<script lang="ts" setup>
const articles = [
  {
    id: "100090369731092480",
    categoryId: "13879727343857664",
    title: "Vite + Vue + TypeScript 项目搭建总结",
    content:
      "# 1. 初始项目\n\n**官网：[Vite | 下一代的前端工具链](https://cn.vitejs.dev/)**\n\n## 1.1 项目搭建\n\n```bash\npnpm create vite\n```\n\n然后按照提示操作，这里选择的是 Vue + TypeScript，生成",
    cover: "https://img.fan223.cn/wallpaper/1.jpg",
    view: 36,
    state: 1,
    flag: "Y",
    createTime: "2023-12-07 14:52:54",
    updateTime: "2026-03-11 00:19:07",
  },
  {
    id: "16105367069638656",
    categoryId: "3346001472081920",
    title: "VS Code - Visual Studio Code 下载安装及常用插件与配置总结",
    content:
      "# 1. 下载安装\n\n**官网：[Visual Studio Code - The open source AI code editor](https://code.visualstudio.com/)**\n\n**下载地址：[Download Visual Studio Code",
    cover: "https://img.fan223.cn/wallpaper/15.jpg",
    view: 75,
    state: 1,
    flag: "Y",
    createTime: "2022-04-05 20:46:29",
    updateTime: "2026-03-10 21:39:25",
  },
  {
    id: "273985955839504384",
    categoryId: "50807724088414208",
    title: "Node.js 版本管理工具（fnm）与包管理工具（pnpm）总结",
    content:
      "# 1. 版本管理工具\n\n## 1.1 nvm，Node Version Manager\n\n**优点**\n\n* 广泛使用，社区成熟，稳定可靠\n* 支持 `.nvmrc` 文件，可在不同项目中自动切换版本\n* 可以在 Unix 系统（Linux/macOS）和 Windows 中使",
    cover: "https://img.fan223.cn/wallpaper/12.jpg",
    view: 25,
    state: 1,
    flag: "Y",
    createTime: "2025-03-31 11:31:53",
    updateTime: "2026-03-10 16:27:18",
  },
  {
    id: "130208158877646848",
    categoryId: "22187186852577280",
    title: "Versions Maven Plugin 与 Maven Flatten Plugin 使用总结",
    content:
      "# 1. Versions Maven Plugin\n\n用于 **检查、更新、管理** Maven 项目依赖和插件版本的官方工具插件\n\n## 1.1 常用命令\n\n1、修改当前项目的 `<version>`\n\n```bash\nmvn versions:set -DnewVersio",
    cover: "https://img.fan223.cn/wallpaper/12.jpg",
    view: null,
    state: null,
    flag: "Y",
    createTime: "2026-02-12 17:01:06",
    updateTime: "2026-03-03 11:46:56",
  },
  {
    id: "137708981314965504",
    categoryId: "13879548351934464",
    title: "Spring Security 开发总结",
    content:
      "**前置知识：**\n\n* **Spring Security 详细相关知识可见：[Spring Security 总结](https://fan223.cn/article/preview/16463289939226624)**\n* **Spring Security 实践可参",
    cover: "https://img.fan223.cn/wallpaper/8.png",
    view: 29,
    state: 1,
    flag: "Y",
    createTime: "2024-03-20 10:15:50",
    updateTime: "2026-01-22 08:48:29",
  },
  {
    id: "51448444530905088",
    categoryId: "22187186852577280",
    title: "Docker Desktop 下载安装及使用总结",
    content:
      "# 1. 下载安装\n\n**官网地址：[Docker: Accelerated Container Application Development](https://www.docker.com/)**\n\n1、进入官网，点击 Download Docker Desktop，选择对应",
    cover: "https://img.fan223.cn/wallpaper/12.jpg",
    view: null,
    state: null,
    flag: "Y",
    createTime: "2025-07-10 08:58:06",
    updateTime: "2026-01-20 10:56:41",
  },
  {
    id: "106977080000020480",
    categoryId: "22187186852577280",
    title: "Junit 6 单元测试总结",
    content:
      "# 1. 引入依赖\n\n这里使用 Spring Boot\n\n```xml\n<dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot",
    cover: "https://img.fan223.cn/wallpaper/12.jpg",
    view: null,
    state: null,
    flag: "Y",
    createTime: "2025-12-10 14:29:05",
    updateTime: "2026-01-20 10:57:07",
  },
  {
    id: "105380997129994240",
    categoryId: "3346001472081920",
    title: "JetBrains Toolbox App 下载安装及使用总结",
    content:
      "# 1. 下载安装\n\n**下载地址：[JetBrains Toolbox App: Manage Your Tools with Ease](https://www.jetbrains.com/toolbox-app/)**\n\n下载完成后按照提示安装\n\n# 2. 使用\n\n可以使用",
    cover: "https://img.fan223.cn/wallpaper/12.jpg",
    view: null,
    state: null,
    flag: "Y",
    createTime: "2025-12-06 04:46:49",
    updateTime: "2026-01-20 10:57:23",
  },
  {
    id: "16291350281043968",
    categoryId: "3346001472081920",
    title: "IntelliJ IDEA 下载安装配置及插件快捷键总结",
    content:
      "# 1. 下载安装\n\n**可见 [JetBrains Toolbox App 下载安装及使用总结](https://fan223.cn/article/preview/105380997129994240)**\n\n1、下载完成后，进入安装目录，修改 `bin\\idea.prope",
    cover: "https://img.fan223.cn/wallpaper/8.png",
    view: 246,
    state: 1,
    flag: "Y",
    createTime: "2022-07-01 09:05:31",
    updateTime: "2025-12-23 14:44:47",
  },
  {
    id: "190120303376068608",
    categoryId: "22187186852577280",
    title: "Checkstyle 使用总结",
    content:
      "# 1. 使用\n\n**GitHub 地址：[checkstyle/checkstyle: Checkstyle is a development tool to help programmers write Java code that adheres to a coding s",
    cover: "https://img.fan223.cn/wallpaper/12.jpg",
    view: 16,
    state: 1,
    flag: "Y",
    createTime: "2024-08-12 01:19:43",
    updateTime: "2025-12-15 17:26:23",
  },
  {
    id: "16109021763846144",
    categoryId: "50807724088414208",
    title: "Git 下载安装及配置与常用命令使用总结",
    content:
      "# 1. 下载安装\n\n**官网地址：[Git](https://git-scm.com/)**\n\n**下载地址：[Git - Downloads](https://git-scm.com/downloads)**\n\n1、进入官网，点击 Downloads 下载\n\n![](http",
    cover: "https://img.fan223.cn/wallpaper/1.jpg",
    view: 85,
    state: 1,
    flag: "Y",
    createTime: "2022-01-26 21:01:00",
    updateTime: "2025-12-07 04:45:26",
  },
  {
    id: "23923537148534784",
    categoryId: "15571596780060672",
    title: "SQL 常用操作总结",
    content:
      "# 1. 空白字符\n\n* 换行符：`\\n`\n* 回车符：`\\r`\n* 制表符：`\\t`\n* 正则：`\\s`\n\n1、MySQL\n\n```sql\n-- 查询替换换行符\nupdate table set column = replace(column, '\\n', '') where ",
    cover: "https://img.fan223.cn/wallpaper/3.jpg",
    view: 15,
    state: 1,
    flag: "Y",
    createTime: "2023-05-11 10:33:06",
    updateTime: "2025-10-30 14:53:05",
  },
];
</script>

<style lang="scss" scoped></style>
