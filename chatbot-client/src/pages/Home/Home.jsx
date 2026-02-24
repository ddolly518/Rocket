import { GroupWrapper } from "../../components/GroupWrapper/GroupWrapper";

import "./Home.css";

export const Home = () => {
  const handleLogout = async () => {
    window.location.href = "/logout";
  };

  const handleChat = async () => {
    window.location.href = "/conversations";
  };

  return (
    <div className="home">
      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="Logout"
        onClick={handleLogout}
      />

      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="Chat"
        onClick={handleChat}
      />
    </div>
  );
};

export default Home;
