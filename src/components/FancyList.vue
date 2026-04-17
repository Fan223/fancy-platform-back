<template>
  <ul>
    <li v-for="item in items" :key="item.id" class="py-3">
      <slot :item="item" />
    </li>
  </ul>
</template>

<script setup lang="ts" generic="T extends { id: string | number }">
interface Props<T> {
  items: T[];
  loading?: boolean;
}

const props = defineProps<Props<T>>();
</script>
