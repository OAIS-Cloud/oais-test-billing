export const useStateStore = defineStore('state', () => {
  const mobileMenuOpen = ref(false);

  function setMobileMenuOpen(open: boolean) {
    mobileMenuOpen.value = open;
  }

  watch(mobileMenuOpen, value => {
    document.body.classList.toggle('overflow-hidden', value);
  });

  return {
    mobileMenuOpen,
    setMobileMenuOpen,
  };
});
