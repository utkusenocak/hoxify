import React from "react";
import UserSignupPage from "../pages/UserSignupPage";
import UserLoginPage from "../pages/UserLoginPage";
import LanguageSelector from "../components/LanguageSelector";
import HomePage from "../pages/HomePage";
import UserPage from "../pages/UserPage";

function App() {
  return (
    <div className="row">
      <UserPage />
      <LanguageSelector />
    </div>
  );
}

export default App;
