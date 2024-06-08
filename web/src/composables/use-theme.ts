export function useTheme() {
  const colorMode = useColorMode();

  function toggleTheme() {
    colorMode.preference = colorMode.preference === 'light' ? 'dark' : 'light';
  }

  return {
    toggleTheme,
  };
}
