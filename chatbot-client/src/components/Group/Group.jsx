import "./Group.css";

export const Group = ({
    className,
    rectangleClassName,
    divClassName,
    text,
    value,
    onChange,
    type = "text"
}) => {
    return (
        <div className={`group ${className}`}>
            <div className="div-wrapper">
                <div className="div">
                    <div className={`rectangle ${rectangleClassName}`} />
                    
                    <input
                        type={type}
                        value={value}
                        onChange={onChange}
                        placeholder={text}
                        className={`enter-email ${divClassName}`}
                    />
                </div>
            </div>
        </div>
    );
};

export default Group;