import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/axiosInstance";

import Logo from "../../assets/logo.png";

import "./Delete.css";

export const Delete = () => {
  const [inputId, setInputId] = useState("");
  const [message, setMessage] = useState(null); // 성공/실패 메시지

  const navigate = useNavigate();

  const handleDelete = async () => {
    if (inputId.trim() === "") return;

    setMessage(null);

    try {
      await api.delete(`/conversations/${Number(inputId)}`);

      setMessage({ type: "success", text: "삭제되었습니다." });

    } catch (err) {
      setMessage({
        type: "error",
        text:
          err.response?.data?.message ||
          "오류 발생",
      });
    }
  };

  return (
    <div className="delete">
      <img className="image" alt="Image" src={Logo} onClick={() => navigate("/main")} style={{cursor: "pointer"}}/>
      
      <h2>대화 삭제</h2>

      <div className="input-section">
        <input
          type="number"
          placeholder="삭제할 Conversation ID 입력"
          value={inputId}
          onChange={(e) => setInputId(e.target.value)}
        />
        <button onClick={handleDelete}>
          삭제
        </button>
      </div>

      {message && (
        <div
          className={
            message.type === "success"
              ? "success-message"
              : "error-message"
          }
        >
          {message.text}
        </div>
      )}
    </div>
  );
};

export default Delete;