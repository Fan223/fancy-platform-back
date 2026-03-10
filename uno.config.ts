import {
  defineConfig,
  presetAttributify,
  presetIcons,
  presetTagify,
  presetTypography,
  presetWebFonts,
  presetWind4,
} from "unocss";

export default defineConfig({
  presets: [
    presetWind4(),
    presetIcons(),
    presetAttributify(),
    presetTypography(),
    presetWebFonts(),
    presetTagify(),
  ],
});
