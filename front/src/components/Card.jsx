import React from "react";
import './Card.css';
import {Link} from "react-router-dom";

function Card(props) {
    const {children, title} = props;

    return (
       <div className="Card">
            {title && <h3>{title}</h3>}
            {children && <h7>{children}</h7>}
       </div>
    )
}

export default Card;
