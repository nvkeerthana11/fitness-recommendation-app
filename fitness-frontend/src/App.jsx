import { Box, Button, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router";
import { useDispatch } from "react-redux";
import { AuthContext } from "react-oauth2-code-pkce";

import { setCredentials } from "./store/authSlice";
import ActivityForm from "./ActivityForm";
import ActivityList from "./ActivityList";
import ActivityDetail from "./ActivitiesDetail";


// ---------- Activities Page ----------
const ActivitiesPage = () => {
const[refresh,setRefresh] = useState(0);
  return (
    <Box sx={{maxWidth:1000,mx:"auto", ml: 70,mt: 10}}>
      <ActivityForm onActivityAdded={() => setRefresh(r => r + 1)} />
      <ActivityList refresh={refresh} />
    </Box>
  );
};


// ---------- App ----------
function App() {
  const { token, tokenData, logIn, logOut } = useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  // Restore auth into Redux
  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    }
  }, [token, tokenData, dispatch]);

  // Login screen
  if (!token) {
    return (
      <Box
      sx={{

          height: "100vh",
          width:"100vw", // 👈 full width
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center"}}
      >
        <Typography variant="h4" gutterBottom>
          Welcome to the Fitness Tracker App
        </Typography>
        <Typography variant="subtitle1" sx={{ mb: 3 }}>
          Please login to access your activities
        </Typography>
        <Button variant="contained" size="large" onClick={() => logIn()}>
          LOGIN
        </Button>
      </Box>
    );
  }

  // Wait for auth to hydrate Redux
  if (!authReady) {
    return <Typography sx={{ p: 2 }}>Loading...</Typography>;
  }

  return (
    <Router>
      <Box sx={{    width: "100%",minHeight:"100vh",
 p: 2 }}>
        <Button
          variant="contained"
          color="secondary"
          onClick={logOut}
          sx={{ mb: 2 }}
        >
          Logout
        </Button>

        <Routes>
          <Route path="/activities" element={<ActivitiesPage />} />
          <Route path="/activities/:id" element={<ActivityDetail />} />
          <Route path="/" element={<Navigate to="/activities" replace />} />
        </Routes>
      </Box>
    </Router>
  );
}

export default App;
