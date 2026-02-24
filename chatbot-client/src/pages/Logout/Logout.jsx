import { GroupWrapper } from "../../components/GroupWrapper/GroupWrapper";
import { Link } from "react-router-dom";
import api from "../../api/axiosInstance";

import "./Logout.css";

export const Logout = () => {
  const handleLogout = async () => {
    try {
      const response = await api.post("/auth/logout");
      console.log("로그아웃 성공:", response.data);
      window.location.href = "/";
    } catch (err) {
      console.error("로그아웃 실패:", err.response?.data || err.message);
    }
  };

  return (
    <div className="logout">
      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="Logout"
        onClick={handleLogout}
      />
    </div>
  );
};

export default Logout;
