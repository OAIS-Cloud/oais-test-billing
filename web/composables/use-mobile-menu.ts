export function useMobileMenu() {
  const appState = useStateStore();
  const { mobileMenuOpen } = storeToRefs(appState);

  return {
    setMobileMenuOpen: appState.setMobileMenuOpen,
    mobileMenuOpen,
  };
}
