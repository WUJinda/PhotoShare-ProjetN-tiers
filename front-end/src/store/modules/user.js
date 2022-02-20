import { getToken, setToken, removeToken } from '@/utils/auth'
import { asyncRoutes, constantRoutes, resetRouter } from '@/router'
import userService from '@/services/userService'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    headPortrait: '',
    introduction: '',
    roles: [],
    routers: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    console.log(state)
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_ROUTERS: (state, routers) => {
    state.addRouters = routers
    state.routers = constantRoutes.concat(routers)
  }
}

const permission = {
  state: {
    routers: constantRoutes,
    addRouters: []
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      userService.login({ username: username.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.data)
        setToken(data.data)
        resolve()
      }).catch(error => {
        console.log(error)
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      userService.getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          return reject('Verification failed, please Login again.')
        }

        const { username, avatar, roles } = data

        console.log('username=', username)

        commit('SET_NAME', username)
        commit('SET_AVATAR', avatar)
        commit('SET_ROLES', roles)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  updatePassword({ commit }, newPassword) {
    return new Promise((resolve, reject) => {
      userService.updatePassword(newPassword).then(res => {
        const token = res.data.token
        commit('SET_TOKEN', token)
        setToken(token)
        resolve()
      })
    })
  },
  // Dynamically generate menus based on roles and permissions
  GenerateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      console.log('role', roles)
      const accessedRouters = asyncRoutes.filter(v => {
        return true
      })
      commit('SET_ROUTERS', accessedRouters)
      resolve()
    })
  },
  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      userService.logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  permission
}

