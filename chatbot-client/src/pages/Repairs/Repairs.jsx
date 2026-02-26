import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/axiosInstance";

import Logo from "../../assets/logo.png";

import "./Repairs.css";

export const Repairs = () => {
  const [conversations, setConversations] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchConversations = async () => {
      try {
        const response = await api.get("/as/repair");
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
    <div className="repairs">
      <img
        className="image"
        alt="Image"
        src={Logo}
        onClick={() => navigate("/main")}
        style={{ cursor: "pointer" }}
      />

      <h2>수리접수 목록</h2>

      {conversations.length === 0 ? (
        <div>대화가 없습니다.</div>
      ) : (
        <ul>
          {conversations.map((repair) => (
            <li key={repair.id} className="repair-item">
              <div>접수번호: {repair.id}</div>
              <div>제품: {repair.product}</div>
              <div>문제: {repair.issue}</div>
              <div>상태: {repair.status}</div>
              <div>접수일: {repair.createdAt}</div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Repairs;
