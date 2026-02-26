import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/axiosInstance";

import Logo from "../../assets/logo.png";

import "./Conversations.css";

export const Conversations = () => {
  const [conversations, setConversations] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchConversations = async () => {
      try {
        const response = await api.get("/conversations");
        setConversations(response.data.data);
      } catch (err) {
        console.error(
          "채팅 메시지 전송 실패:",
          err.response?.data || err.message,
        );
      }
    };

    fetchConversations();
  }, []);

  return (
    <div className="conversations">
      <img className="image" alt="Image" src={Logo} onClick={() => navigate("/main")} style={{cursor: "pointer"}}/>
      
      <h2>대화 목록</h2>

      {conversations.length === 0 ? (
        <div>대화가 없습니다.</div>
      ) : (
        <ul>
          {conversations.map((conv) => (
            <li key={conv.id} className="conversation-item">
              ({conv.id}, {conv.title})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Conversations;
