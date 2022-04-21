import React from "react";
import logo from "../assets/hoaxify.png";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useSelector, useDispatch } from "react-redux";
import { logoutSuccess } from "../redux/authSlice";

function TopBar() {
  const dispatch = useDispatch();
  const { t } = useTranslation();
  const { isLoggedIn, username } = useSelector((state) => state.auth);
  let links = (
    <ul className="navbar-nav ml-auto">
      <li>
        <Link className="nav-link" to="/login">
          {t("Login")}
        </Link>
      </li>
      <li>
        <Link className="nav-link" to="/signup">
          {t("Sign Up")}
        </Link>
      </li>
    </ul>
  );
  if (isLoggedIn) {
    links = (
      <ul className="navbar-nav ml-auto">
        <Link className="nav-link" to={`/user/${username}`}>
          {username}
        </Link>
        <li
          className="nav-link"
          onClick={() => dispatch(logoutSuccess())}
          style={{ cursor: "pointer" }}
        >
          {t("Logout")}
        </li>
      </ul>
    );
  }

  return (
    <div className="shadow-sm bg-light mb-2">
      <nav className="navbar navbar-light container navbar-expand">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">
            <img src={logo} width="60" alt="Hoxify Logo" />
            Hoxify
          </Link>
          {links}
        </div>
      </nav>
    </div>
  );
}

export default TopBar;
