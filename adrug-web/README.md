# adrug-web

DrugERP 医药 ERP 前端，Vue 3 + Vite + Element Plus。

## 界面布局

- 左：菜单（来自后端菜单服务 `/svc/common/menu/tree/{tenantId}`）
- 顶：页签（多页签，可关闭，keep-alive 缓存）
- 中：内容（router-view）

## 运行

```bash
npm install
npm run dev
```

开发服务器默认 `http://localhost:5173`，`/svc/**` 前缀代理到网关 `http://localhost:8081`。

## 菜单与租户

- 侧边栏菜单由后端菜单服务下发（`src/api/menu.js`），后端不可用时回退到本地默认菜单（`src/mock/menu.js`）。
- 顶部「租户 ID」输入框用于演示「默认菜单 + 租户自定义菜单覆盖」：`0` 为默认菜单，其他值为租户自定义菜单（按 `menuCode` 覆盖默认菜单同名项）。
- 菜单 `component` 字段对应 `src/views` 下的组件路径（不含 `.vue` 后缀），通过 `import.meta.glob` 自动映射；动态路由 name 取 `menuCode`，并作为 keep-alive 缓存键，因此各视图组件需 `defineOptions({ name: '<menuCode>' })`。

## 目录结构

```
src/
  api/          # 后端接口封装
  mock/         # 本地默认菜单兜底
  stores/       # Pinia：app(租户)、menu(菜单+动态路由)、tabs(页签)
  router/       # 路由
  layout/       # ERP 布局（侧边栏/页签/内容）
  views/        # 页面组件
```
