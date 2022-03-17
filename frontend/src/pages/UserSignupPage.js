import React from "react";
import { signup, changeLanguage } from "../api/apiCalls";
import Input from "../components/Input";
import { withTranslation } from "react-i18next";

class UserSignupPage extends React.Component {
  state = {
    username: null,
    displayName: null,
    password: null,
    passwordRepeat: null,
    pendingApiCall: false,
    errors: {},
  };
  onChange = (event) => {
    const { name, value } = event.target;
    const errors = { ...this.state.errors };
    const { t } = this.props;
    errors[name] = undefined;
    if (name === "password" || name === "passwordRepeat") {
      if (name === "password" && value !== this.state.passwordRepeat) {
        errors.passwordRepeat = t("Password mismatch");
      } else if (name === "passwordRepeat" && value !== this.state.password) {
        errors.passwordRepeat = t("Password mismatch");
      } else {
        errors.passwordRepeat = undefined;
      }
    }
    this.setState({
      [name]: value,
      errors,
    });
  };

  onClickSignup = async (event) => {
    event.preventDefault();
    const { username, displayName, password } = this.state;
    const body = {
      username,
      displayName,
      password,
    };
    this.setState({ pendingApiCall: true });
    try {
      const response = await signup(body);
    } catch (error) {
      if (error.response.data.validationErrors) {
        this.setState({ errors: error.response.data.validationErrors });
      }
    }
    this.setState({ pendingApiCall: false });
  };

  onChangeLanguage = (language) => {
    const { i18n } = this.props;
    i18n.changeLanguage(language);
    changeLanguage(language);
  };

  render() {
    const { pendingApiCall, errors } = this.state;
    const { username, displayName, password, passwordRepeat } = errors;
    const { t } = this.props;
    return (
      <div className="container">
        <form>
          <h1 className="text-center">{t("Sign Up")}</h1>
          <Input
            name="username"
            label={t("Username")}
            error={username}
            onChange={this.onChange}
          />
          <Input
            name="displayName"
            label={t("Display Name")}
            error={displayName}
            onChange={this.onChange}
          />
          <Input
            name="password"
            label={t("Password")}
            error={password}
            type="password"
            onChange={this.onChange}
          />
          <Input
            name="passwordRepeat"
            label={t("Password Repeat")}
            error={passwordRepeat}
            type="password"
            onChange={this.onChange}
          />
          <div className="text-center">
            <button
              className="btn btn-primary"
              onClick={this.onClickSignup}
              disabled={pendingApiCall || passwordRepeat !== undefined}
            >
              {pendingApiCall && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              {t("Sign Up")}
            </button>
          </div>
          <div>
            <img
              src="https://flagcdn.com/h24/tr.png"
              srcset="https://flagcdn.com/h48/tr.png 2x"
              height="24"
              alt="Turkey"
              onClick={() => this.onChangeLanguage("tr")}
              style={{ cursor: "pointer" }}
            />
            <img
              src="https://flagcdn.com/h24/us.png"
              srcset="https://flagcdn.com/h48/us.png 2x"
              height="24"
              alt="United States"
              onClick={() => this.onChangeLanguage("en")}
              style={{ cursor: "pointer" }}
            />
          </div>
        </form>
      </div>
    );
  }
}

const UserSignupPageWithTranslation = withTranslation()(UserSignupPage);

export default UserSignupPageWithTranslation;
