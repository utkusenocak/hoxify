import { createSlice } from "@reduxjs/toolkit";

export const authSlice = createSlice({
  name: "auth",
  initialState: {
    isLoggedIn: true,
    username: "user1",
    displayName: "display1",
    image: null,
    password: "P4ssword",
  },
  reducers: {
    logoutSuccess: (state) => {
      state.isLoggedIn = false;
      state.username = undefined;
      state.displayName = undefined;
      state.image = undefined;
      state.password = undefined;
    },
  },
});

export const { logoutSuccess } = authSlice.actions;
export default authSlice.reducer;
