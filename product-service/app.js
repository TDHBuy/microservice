const express = require("express");
const {MongoClient} = require("mongodb");

const app = express();
const port = process.env.PORT || 3000;
const uri = process.env.MONGO_URI

MongoClient.connect(uri)
.then(client => {
    console.log("Connected to MongoDB");
    const db = client.db();

    app.get("/product-service", (req, res) => {
        res.send("Product service is connected to MongoDB");
    });

    app.listen(port, () => {
        console.log("Listening on port " + port);
    })
})
.catch(err => {
    console.error("MongoDB connection failed: ",err);
    process.exit(1);
})