import { Card, CardContent, Grid2, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { useSelector } from 'react-redux';
import { getActivities } from './services/api';

const ActivityList = ({refresh}) => {
  const [activities, setActivities] = useState([]);
  const navigate = useNavigate();
  const token = useSelector(state => state.auth.token);

  const fetchActivities = async () => {
    try {
      const response = await getActivities();
      setActivities(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (!token) return;
    fetchActivities();
  }, [token,refresh]);
  

  return (
    <Grid2 container spacing={2}>
      {activities.map(activity => (
        <Grid2 key={activity.id} xs={12} sm={6} md={4}>
          <Card
            sx={{ cursor: 'pointer' }}
            onClick={() => navigate(`/activities/${activity.id}`)}
          >
            <CardContent>
              <Typography variant="h6">{activity.type}</Typography>
              <Typography>Duration: {activity.duration}</Typography>
              <Typography>Calories: {activity.caloriesBurned}</Typography>
            </CardContent>
          </Card>
        </Grid2>
      ))}
    </Grid2>
  );
};

export default ActivityList;
