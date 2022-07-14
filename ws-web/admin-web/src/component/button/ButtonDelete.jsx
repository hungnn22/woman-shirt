import React from "react";

const ButtonDelete = ({ title }) => {
  return (
    <button class="btn btn-danger btn-icon-split">
      <span class="icon text-white-50">
        <i class="fas fa-trash"></i>
      </span>
      <span class="text">{title && title}</span>
    </button>
  );
};

export default ButtonDelete;
