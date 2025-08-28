import { useEffect, useState, useMemo } from "react";
import { useParams, Link } from "react-router-dom";
import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import markerIconPng from 'leaflet/dist/images/marker-icon.png';
import markerShadowPng from 'leaflet/dist/images/marker-shadow.png';

const UserProfile = () => {
  const { id } = useParams();
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState(null);
  const [selectedPos, setSelectedPos] = useState(null);
  const [showMap, setShowMap] = useState(false);
  const username = localStorage.getItem("username");

  const markerIcon = useMemo(
    () =>
      new L.Icon({
        iconUrl: markerIconPng,
        shadowUrl: markerShadowPng,
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        shadowSize: [41, 41],
        shadowAnchor: [12, 41],
      }),
    []
  );

  const LocationSelector = () => {
    useMapEvents({
      click(e) {
        setSelectedPos(e.latlng);
      },
    });
    return selectedPos ? <Marker position={selectedPos} icon={markerIcon} /> : null;
  };

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const token = localStorage.getItem("token");

        const response = await fetch(`http://localhost:8080/user/${id}`, {
          headers: {
            Authorization: token ? `Bearer ${token}` : "",
          },
        });

        if (!response.ok) {
          const msg = await response.text();
          setError(msg || "No se pudo cargar el perfil");
          return;
        }

        const data = await response.json();
        setProfile(data);
        if (data.store && typeof data.latitude === "number" && typeof data.longitude === "number") {
          setSelectedPos({ lat: data.latitude, lng: data.longitude });
        }
      } catch (err) {
        setError("Error de conexión con el servidor");
        console.error(err);
      }
    };

    fetchProfile();
  }, [id]);

  const handleEnableStore = async () => {
    if (!selectedPos) {
      alert("Por favor selecciona una ubicación en el mapa");
      return;
    }

    try {
      const token = localStorage.getItem("token");

      const response2 = await fetch(`http://localhost:8080/user/enableStore`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: token ? `Bearer ${token}` : "",
        },
        body: JSON.stringify({
          id: profile.id,
          latitude: selectedPos.lat,
          longitude: selectedPos.lng,
        }),
      });

      if (!response2.ok) {
        const msg = await response2.text();
        alert(msg || "No se pudo habilitar la tienda");
        return;
      }

      setProfile({ ...profile, store: true, latitude: selectedPos.lat, longitude: selectedPos.lng });
      setShowMap(false);
      localStorage.setItem("isStore", true);
      alert("Ahora eres tienda");
    } catch (err) {
      alert("Error al conectar con el servidor");
      console.error(err);
    }
  };

  return (
    <div className="d-flex vh-100 align-items-center justify-content-center bg-light">
      <div className="card shadow-sm" style={{ maxWidth: 500, width: "100%" }}>
        <div className="card-body p-4">
          <h3 className="card-title text-center mb-4">Perfil de usuario</h3>

          {error && (
            <div className="alert alert-danger" role="alert">
              {error}
            </div>
          )}

          {profile && (
            <>
              <div className="mb-3">
                <strong>Usuario:</strong> {profile.username}
              </div>
              <div className="mb-3">
                <strong>Nombre:</strong> {profile.firstName}
              </div>
              <div className="mb-3 d-flex align-items-center justify-content-between">
                <div>
                  <strong>¿Es tienda?:</strong> {profile.store ? "Sí" : "No"}
                </div>
                {!profile.store && !showMap && username === profile.username && (
                  <button className="btn btn-primary btn-sm" onClick={() => setShowMap(true)}>
                    Volverse tienda
                  </button>
                )}
              </div>

              {profile.store && (
                <>
                  <div className="mb-2">
                    <strong>Localización:</strong>
                  </div>
                  {selectedPos && (
                    <MapContainer
                      center={selectedPos}
                      zoom={13}
                      style={{ height: "300px", width: "100%" }}
                    >
                      <TileLayer
                        attribution='&copy; <a href="https://osm.org">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                      />
                      <Marker position={selectedPos} icon={markerIcon} />
                    </MapContainer>
                  )}
                </>
              )}

              {!profile.store && showMap && (
                <>
                  <MapContainer
                    center={selectedPos || { lat: 40.4168, lng: -3.7038 }}
                    zoom={13}
                    style={{ height: "300px", width: "100%" }}
                  >
                    <TileLayer
                      attribution='&copy; <a href="https://osm.org">OpenStreetMap</a> contributors'
                      url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    <LocationSelector />
                  </MapContainer>
                  <button className="btn btn-success w-100 mt-2" onClick={handleEnableStore}>
                    Confirmar ubicación y volverse tienda
                  </button>
                </>
              )}
            </>
          )}

          <div className="text-center mt-3">
            <Link to="/" className="btn btn-secondary w-100">
              Volver
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfile;
