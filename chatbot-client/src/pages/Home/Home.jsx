import { GroupWrapper } from "../../components/GroupWrapper/GroupWrapper";
import api from "../../api/axiosInstance";

import "./Home.css";

export const Home = () => {
  const handleLogout = async () => {
    try {
      const response = await api.post("/auth/logout");
      console.log("로그아웃 성공:", response.data);
      window.location.href = "/";
    } catch (err) {
      console.error("로그아웃 실패:", err.response?.data || err.message);
    }
  };

  const handleChat = async () => {
    window.location.href = "/chat/completions";
  };

  const handleConversations = async () => {
    window.location.href = "/conversations";
  };

  const handleConversation = async () => {
    window.location.href = "/conversation";
  };

  const handleMessages = async () => {
    window.location.href = "/messages";
  };

  const handleDelete = async () => {
    window.location.href = "/delete";
  };

  const handleRepair = async () => {
    window.location.href = "/repair";
  };
  
  const handleRepairs = async () => {
    window.location.href = "/repairs";
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
        text="메시지 전송"
        onClick={handleChat}
      />

      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="수리 접수 채팅 메시지 전송"
        onClick={handleRepair}
      />

      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="대화 목록 조회"
        onClick={handleConversations}
      />

      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="대화 상세 조회"
        onClick={handleConversation}
      />

      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="대화 메시지 조회"
        onClick={handleMessages}
      />

      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="수리접수 목록 조회"
        onClick={handleRepairs}
      />

      <GroupWrapper
        className="group-14"
        rectangleClassName="group-14-instance"
        text="대화 삭제"
        onClick={handleDelete}
      />
    </div>
  );
};

export default Home;
