import React, { useState, useEffect, createContext } from "react";

const UserContext = createContext("");
function UserProvider({ children }) {
  const [category, setCategory] = useState("");
  const [keySearch, setKeySearch] = useState("");
  const [targetSearch] = useState(["category"]);
  const [data, setData] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/user")
      .then((result) => result.json())
      .then((data) => {
        setData(data.data);
      });
  }, [setData]);

  const value = {
    data,
    category,
    setCategory,
    keySearch,
    setKeySearch,
    targetSearch,
  };

  return (
    <UserContext.Provider value={value}>{children}</UserContext.Provider>
  );
}

export { UserProvider, UserContext };
