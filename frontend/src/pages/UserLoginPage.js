import React from "react";
import Input from "../components/Input";
import { withTranslation } from "react-i18next";
import ButtonWithProgress from "../components/ButtonWithProgress";
import { withApiProgress } from "../shared/ApiProgress";
import { connect } from "react-redux";
import { loginHandler } from "../redux/authSlice";

class UserLoginPage extends React.Component {
  // static contextType = Authentication;
  state = {
    username: null,
    password: null,
    error: null,
  };

  onChange = (event) => {
    const { name, value } = event.target;
    this.setState({
      [name]: value,
      error: null,
    });
  };

  onClickLogin = async (event) => {
    event.preventDefault();
    const { username, password } = this.state;

    const creds = {
      username,
      password,
    };
    const { history, dispatch } = this.props;
    const { push } = history;

    this.setState({
      error: null,
    });
    try {
      await dispatch(loginHandler(creds));
      push("/");
    } catch (apiError) {
      this.setState({
        error: apiError.response.data.message,
      });
    }
  };

  render() {
    const { t, pendingApiCall } = this.props;
    const { username, password, error } = this.state;

    const buttonEnabled = username && password;

    return (
      <div className="container">
        <form>
          <h1 className="text-center">{t("Login")}</h1>
          <Input
            name="username"
            label={t("Username")}
            onChange={this.onChange}
          />
          <Input
            name="password"
            label={t("Password")}
            type="password"
            onChange={this.onChange}
          />
          {error && <div className="alert alert-danger">{error}</div>}
          <div className="text-center">
            <ButtonWithProgress
              onClick={this.onClickLogin}
              pendingApiCall={pendingApiCall}
              disabled={!buttonEnabled || pendingApiCall}
              text={t("Login")}
            />
          </div>
        </form>
      </div>
    );
  }
}
const UserLoginPageWithApiProgress = withApiProgress(
  UserLoginPage,
  "/api/1.0/auth"
);
const UserLoginPageWithTranslation = withTranslation()(
  UserLoginPageWithApiProgress
);

export default connect()(UserLoginPageWithTranslation);
