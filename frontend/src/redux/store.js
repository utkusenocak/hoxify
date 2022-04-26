import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../redux/authSlice";
import SecureLS from "secure-ls";

const secureLs = new SecureLS();

const getStateFromStorage = () => {
  const hoaxAuth = secureLs.get("hoax-auth");

  let stateInLocalStorage = {
    isLoggedIn: false,
    username: undefined,
    displayName: undefined,
    image: undefined,
    password: undefined,
  };
  if (hoaxAuth) {
    return hoaxAuth;
  }
  return stateInLocalStorage;
};

const updateStateInStorage = (newState) => {
  secureLs.set("hoax-auth", newState);
};

const store = configureStore({
  reducer: {
    auth: authReducer,
  },
  preloadedState: getStateFromStorage(),
});
store.subscribe(() => {
  updateStateInStorage(store.getState());
});
export default store;
