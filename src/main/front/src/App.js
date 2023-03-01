import logo from './logo.svg';
import './App.css';
import axios from "axios";
import {useState} from "react";

function App() {

  const [parentCategory , setParentCategory] = useState("");
  const [childCategory , setChildCategory] = useState("");


    const handleSubmit = () => {

        const url ="/category"

        let data = {
            branch: parentCategory,
            name: childCategory,
            parentItemCategory:{
                branch: parentCategory, name: "ROOT"
            }
        }

        axios.post(url , JSON.stringify(data),{
            headers: { "Content-Type": `application/json`}
        })
            .then(()=>{
                alert("카테고리 생성 완료!");
            })
            .catch((error)=>alert(error.response.data.message))
    }

  return (
    <div className="App">
        <h2>{parentCategory}</h2>
        <h2>{childCategory}</h2>
      <input
          onChange={(e)=>{setParentCategory(e.target.value)}}
          className = "parentCategory"
          type = "text"
          placeholder= "대분류를 입력하세요"
      />
      <input
          onChange={(e)=>{setChildCategory(e.target.value)}}
          className = "childCategory"
          type = "text"
          placeholder= "소분류를 입력하세요"
      />
      <button onClick={()=>{handleSubmit()}} className = "createCategoryBtn">카테고리 생성</button>
    </div>


  );
}


export default App;
