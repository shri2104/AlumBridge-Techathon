//package com.example.readerapp.MongoAPI
//
//// alumbridge
//app.post('/StoreInstitutedata', async (req, res) => {
//    const collection = alumbridge.collection("StoreInstitutedata");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getInstituteData/:userId', async (req, res) => {
//    const collection = alumbridge.collection("StoreInstitutedata");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//    try {
//        const user = await collection.findOne({ userId: userId }); // Find user by userId
//
//        if (user) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/storeuserid', async (req, res) => {
//    const collection = alumbridge.collection("storeuserid");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getuserid/:email', async (req, res) => {
//    const collection = alumbridge.collection("storeuserid");
//    const email = req.params.email; // Retrieve email from the URL parameter
//    try {
//        const user = await collection.findOne({ email: email }); // Find user by email
//        if (user) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getalljobs/:userId', async (req, res) => {
//    const collection = alumbridge.collection("storealljobs");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//
//    try {
//        const jobs = await collection.find({ userId: userId }).toArray(); // Find all jobs by userId
//
//        if (jobs.length > 0) {
//            res.status(200).send(jobs); // Return all job postings by the user
//        } else {
//            res.status(404).send({ success: false, message: 'No jobs found for this user' }); // If no jobs exist
//        }
//    } catch (error) {
//        console.error("Retrieve Jobs Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getjobcount/:userId', async (req, res) => {
//    const collection = alumbridge.collection("storealljobs");
//    const userId = req.params.userId;
//
//    try {
//        const jobCount = await collection.countDocuments({ userId: userId }); // Count jobs for userId
//        res.status(200).send({ success: true, count: jobCount }); // Send count as response
//    } catch (error) {
//        console.error("Error fetching job count:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//
//app.post('/storealljobs', async (req, res) => {
//    const collection = alumbridge.collection("storealljobs");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//app.post('/storeEventData', async (req, res) => {
//    const collection = alumbridge.collection("Eventdata");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getEvents/:userId', async (req, res) => {
//    const collection = alumbridge.collection("Eventdata");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//
//    try {
//        const events = await collection.find({ userId: userId }).toArray(); // Find all events by userId
//
//        if (events.length > 0) {
//            res.status(200).send(events); // Return all events by the user
//        } else {
//            res.status(404).send({ success: false, message: 'No events found for this user' }); // If no events exist
//        }
//    } catch (error) {
//        console.error("Retrieve Events Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//
//
//app.post('/Storeprofiledata', async (req, res) => {
//    const collection = alumbridge.collection("Profiledata");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error) {
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/storeDonationInfo', async (req,res) => {
//    const collection = alumbridge.collection("storeDonationInfo");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error){
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/Getalldonation/:userId', async (req, res) => {
//    const collection = alumbridge.collection("storeDonationInfo");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//
//    try {
//        const events = await collection.find({ userId: userId }).toArray(); // Find all events by userId
//
//        if (events.length > 0) {
//            res.status(200).send(events); // Return all events by the user
//        } else {
//            res.status(404).send({ success: false, message: 'No donation found for this user' }); // If no events exist
//        }
//    } catch (error) {
//        console.error("Retrieve Events Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/getallprofile/:userId', async (req, res) => {
//    const collection = alumbridge.collection("Profiledata");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//
//    try {
//        const events = await collection.find({ userId: userId }).toArray(); // Find all events by userId
//
//        if (events.length > 0) {
//            res.status(200).send(events); // Return all events by the user
//        } else {
//            res.status(404).send({ success: false, message: 'No donation found for this user' }); // If no events exist
//        }
//    } catch (error) {
//        console.error("Retrieve Events Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/Getstudentprofile/:userId', async (req, res) => {
//    const collection = alumbridge.collection("Profiledata");
//    const userId = req.params.userId; // Retrieve userId from the URL parameter
//    try {
//        const user = await collection.findOne({ userId: userId }); // Find user by userId
//
//        if (user) {
//            res.status(200).send(user); // Return the user data
//        } else {
//            res.status(404).send({ success: false, message: 'User not found' }); // If user doesn't exist
//        }
//    } catch (error) {
//        console.error("Retrieve Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.post('/Storedonationdata02', async (req,res) => {
//    const collection = alumbridge.collection("Eventdata");
//    const data = req.body;
//    try {
//        const result = await collection.insertOne(data);
//        res.status(200).send({ success: true, id: result.insertedId });
//    } catch (error){
//        console.error("Insert Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
//
//app.get('/GetProfiledata', async (req, res) => {
//    const collection = alumbridge.collection("Eventdata");
//    try {
//        const jobs = await collection.find().toArray();
//        res.status(200).send(jobs);
//    } catch (error) {
//        console.error("Retrieve Jobs Error:", error);
//        res.status(500).send({ success: false, error: error.message });
//    }
//});
