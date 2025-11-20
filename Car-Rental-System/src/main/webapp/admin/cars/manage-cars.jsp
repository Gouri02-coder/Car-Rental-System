<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Cars - Car Rental System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Car Rental Admin</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="../dashboard.jsp"><i class="fas fa-tachometer-alt"></i> Dashboard</a>
                <a class="nav-link" href="../../index.jsp"><i class="fas fa-home"></i> Home</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Manage Cars</h2>
            <a href="add-car.jsp" class="btn btn-primary"><i class="fas fa-plus"></i> Add New Car</a>
        </div>

        <div class="card">
            <div class="card-header">
                <h5>Car List</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Image</th>
                                <th>Brand</th>
                                <th>Model</th>
                                <th>Type</th>
                                <th>Price/Day</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>CAR001</td>
                                <td><img src="https://via.placeholder.com/50" alt="Car" class="img-thumbnail" style="width: 50px;"></td>
                                <td>Toyota</td>
                                <td>Camry</td>
                                <td>Sedan</td>
                                <td>$45</td>
                                <td><span class="badge bg-success">Available</span></td>
                                <td>
                                    <a href="edit-car.jsp?id=CAR001" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                                    <button class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>
                                </td>
                            </tr>
                            <tr>
                                <td>CAR002</td>
                                <td><img src="https://via.placeholder.com/50" alt="Car" class="img-thumbnail" style="width: 50px;"></td>
                                <td>Honda</td>
                                <td>CR-V</td>
                                <td>SUV</td>
                                <td>$60</td>
                                <td><span class="badge bg-warning">Rented</span></td>
                                <td>
                                    <a href="edit-car.jsp?id=CAR002" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                                    <button class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>