export function reset(obj) {
  Object.keys(obj).forEach(key => obj[key] = '')
}
