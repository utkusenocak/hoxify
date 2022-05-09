import React from "react";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";

const ProfileCard = (props) => {
  const pathUserName = useParams().username;
  const loggedInUsername = useSelector((state) => state.auth.username);
  let message = "We cannot edit";
  if (pathUserName === loggedInUsername) {
    message = "We can edit";
  }
  return <div>{message}</div>;
};

export default ProfileCard;
