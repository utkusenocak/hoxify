import React, { Component } from "react";
import logo from "../assets/hoaxify.png";
import { Link } from "react-router-dom";
import { withTranslation } from "react-i18next";

class TopBar extends Component {
  state = {
    isLoggedIn: true,
    username: "user1",
  };
  render() {
    const { t } = this.props;
    const { isLoggedIn, username } = this.state;

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
          <li className="nav-link">{t("Logout")}</li>
        </ul>
      );
    }

    return (
      <div className="shadow-sm bg-light mb-2">
        <nav class="navbar navbar-light container navbar-expand">
          <div class="container-fluid">
            <Link class="navbar-brand" to="/">
              <img src={logo} width="60" alt="Hoxify Logo" />
              Hoxify
            </Link>
            {links}
          </div>
        </nav>
      </div>
    );
  }
}

export default withTranslation()(TopBar);
