import { defineConfig } from "tsup";

export default defineConfig([
  {
    entry: ["src/hello-world.ts"],
    outDir: "dist",
    format: "esm",
    minify: false,
    dts: true,
  },
  {
    entry: ["src/hello-world.ts"],
    outDir: "dist",
    format: "esm",
    minify: true,
    dts: false,
    outExtension: () => ({
      js: ".min.js",
    }),
  },
  {
    entry: ["src/hello-world.ts"],
    outDir: "dist",
    format: "esm",
    minify: false,
    dts: false,
    noExternal: ["lit"],
    outExtension: () => ({
      js: ".noExternal.js",
    }),
  },
  {
    entry: ["src/hello-world.ts"],
    outDir: "dist",
    format: "esm",
    minify: true,
    dts: false,
    noExternal: ["lit"],
    outExtension: () => ({
      js: ".noExternal.min.js",
    }),
  },
]);
