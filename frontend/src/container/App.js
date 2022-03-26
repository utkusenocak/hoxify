import React from "react";
import UserSignupPage from "../pages/UserSignupPage";
import UserLoginPage from "../pages/UserLoginPage";
import LanguageSelector from "../components/LanguageSelector";

function App() {
  return (
    <div className="row">
      <div className="col">
        <UserSignupPage />
      </div>
      <div className="col">
        <UserLoginPage />
      </div>

      <LanguageSelector />
    </div>
  );
}

export default App;
