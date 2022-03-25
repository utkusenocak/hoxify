import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import "./bootstrap-override.scss";
import App from "./App";
import UserSignupPage from "./pages/UserSignupPage";
import UserLoginPage from "./pages/UserLoginPage";
import ApiProgress from "./shared/ApiProgress";
import LanguageSelector from "./components/LanguageSelector";
import reportWebVitals from "./reportWebVitals";
import "./i18n";

ReactDOM.render(
  <React.StrictMode>
    <ApiProgress>
      <UserLoginPage />
    </ApiProgress>

    <LanguageSelector />
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
