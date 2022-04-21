import { createSlice } from "@reduxjs/toolkit";

export const authSlice = createSlice({
  name: "auth",
  initialState: {
    isLoggedIn: false,
    username: undefined,
    displayName: undefined,
    image: undefined,
    password: undefined,
  },
  reducers: {
    logoutSuccess: (state) => {
      state.isLoggedIn = false;
      state.username = undefined;
      state.displayName = undefined;
      state.image = undefined;
      state.password = undefined;
    },
    loginSuccess: (state, action) => {
      state.isLoggedIn= true;
      state.username= action.payload.username;
      state.displayName= action.payload.displayName;
      state.image= action.payload.image;
      state.password= action.payload.password;
    }
  },
});

export const { logoutSuccess, loginSuccess } = authSlice.actions;
export default authSlice.reducer;
