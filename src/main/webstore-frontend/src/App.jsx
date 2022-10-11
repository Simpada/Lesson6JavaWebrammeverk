import './App.css'
import {useEffect, useState} from "react";


function ProductList() {
    const [loading, setLoading] = useState(true);
    const [products, setProducts] = useState([]);

    useEffect( () => {
        (async () => {
            const res = await fetch("/api/products");
            setProducts(await res.json());
            setLoading(false);
        })();
    }, [])

    if (loading) {
        return <div>Loading...</div>
    }
    return <ul>{products.map(p => <div>{p.productName} costs {p.price}</div>)}</ul>;

}

function AddProduct() {

    const [productName, setProductName] = useState("");
    const [category, setCategory] = useState("HATS");
    const [price, setPrice] = useState(0);

    async function handleSubmit(e) {
        e.preventDefault();
        await fetch("api/products", {
            method: "post",
            body: JSON.stringify({productName, category, price}),
            headers: {
                "Content-type": "application/json"
            }
        })
    }

    return <div>
        <form onSubmit={handleSubmit}>
            <div><label>Product Name: <input type="text" value={productName} onChange={e => setProductName(e.target.value)}/></label></div>
            <div><label>Category: <input type="text" value={category} onChange={e => setCategory(e.target.value)}/></label></div>
            <div><label>Price: <input type="text" value={price} onChange={e => setPrice(e.target.value)}/></label></div>
            <div>
                <button>Submit</button>
            </div>
        </form>
    </div>;
}


function App() {

  return (
    <div className="App">

        <h1> Simpimpin' Goods and Wares </h1>

        <ProductList/>
        <AddProduct/>

    </div>
  )
}

export default App
