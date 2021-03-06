const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  headPortrait: state => state.user.headPortrait,
  name: state => state.user.name,
  roles: state => state.user.roles,
  routers: state => state.user.routers
}
export default getters
