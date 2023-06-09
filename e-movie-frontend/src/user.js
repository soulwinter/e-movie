import Cookies from 'js-cookie'

export const setUserType = (type) => {
  Cookies.set('UserType', type)
}


export const getUserType = () => {
  const user = Cookies.get('UserType')
  if (user) return user
  else return 0
}

export const deleteUserType = () => {
    Cookies.remove('UserType');
}