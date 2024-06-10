export { default as AppHeader } from './AppHeader.vue';
export { default as AppSidebar } from './AppSidebar.vue';

interface SidebarItem {
  category?: string;
  items: {
    name: string;
    href: string;
  }[];
}

export const sidebarItems: SidebarItem[] = [
  {
    category: 'layout.sidebar.menu.home.category_name',
    items: [
      {
        name: 'layout.sidebar.menu.home.home',
        href: '/',
      },
    ],
  },
  {
    category: 'layout.sidebar.menu.management.category_name',
    items: [
      {
        name: 'layout.sidebar.menu.management.currencies',
        href: '/currencies',
      },
      {
        name: 'layout.sidebar.menu.management.contracts',
        href: '/contracts',
      },
    ],
  },
];
