import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../redux/authSlice";
const hoaxAuth = localStorage.getItem("hoax-auth");
let stateInLocalStorage = {
  isLoggedIn: false,
  username: undefined,
  displayName: undefined,
  image: undefined,
  password: undefined,
};
if (hoaxAuth) {
  try {
    stateInLocalStorage = JSON.parse(hoaxAuth);
  } catch (error) {}
}

const store = configureStore({
  reducer: {
    auth: authReducer,
  },
  preloadedState: stateInLocalStorage,
});
store.subscribe(() => {
  localStorage.setItem("hoax-auth", JSON.stringify(store.getState()));
});
export default store;
