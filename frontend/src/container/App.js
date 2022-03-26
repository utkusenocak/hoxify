import React from "react";
import ApiProgress from "../shared/ApiProgress";
import UserSignupPage from "../pages/UserSignupPage";
import UserLoginPage from "../pages/UserLoginPage";
import LanguageSelector from "../components/LanguageSelector";

function App() {
  return (
    <div className="row">
      <div className="col">
        <ApiProgress path="/api/1.0/users">
          <UserSignupPage />
        </ApiProgress>
      </div>
      <div className="col">
        <ApiProgress path="/api/1.0/auth">
          <UserLoginPage />
        </ApiProgress>
      </div>

      <LanguageSelector />
    </div>
  );
}

export default App;
