import React from "react";
import UserSignupPage from "../pages/UserSignupPage";
import UserLoginPage from "../pages/UserLoginPage";
import LanguageSelector from "../components/LanguageSelector";
import HomePage from "../pages/HomePage";
import UserPage from "../pages/UserPage";
import { HashRouter, Route, Redirect, Switch } from "react-router-dom";

function App() {
  return (
    <div>
      <HashRouter>
        <Switch>
          <Route exact path="/" component={HomePage} />
          <Route path="/login" component={UserLoginPage} />
          <Route path="/signup" component={UserSignupPage} />
          <Route path="/user/:username" component={UserPage} />
          <Redirect to="/" />
        </Switch>
      </HashRouter>
      <LanguageSelector />
    </div>
  );
}

export default App;
