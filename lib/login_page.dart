import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final _idController = TextEditingController();
  final _pwController = TextEditingController();

  Future<void> _login() async {
    final id = _idController.text;
    final pw = _pwController.text;

    final url = Uri.parse('http://10.0.2.2:8080/api/auth/login');
    final response = await http.post(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'id': id, 'pw': pw}),
    );

    if (response.statusCode == 200) {
      print("로그인 성공");
      Navigator.pushNamed(context, '/home');
    } else if (response.statusCode == 401) {
      print("로그인 실패");
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('로그인 실패! 아이디와 비밀번호를 확인해주세요.')),
      );
    } else {
      print("알 수 없는 오류 발생: ${response.statusCode}");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("로그인")),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextFormField(
                controller: _idController,
                decoration: InputDecoration(labelText: '아이디'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return '아이디를 입력해주세요.';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _pwController,
                decoration: InputDecoration(labelText: '비밀번호'),
                obscureText: true,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return '비밀번호를 입력해주세요.';
                  }
                  return null;
                },
              ),
              SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  if (_formKey.currentState?.validate() ?? false) {
                    _login();
                  }
                },
                child: Text('로그인'),
              ),
              TextButton(
                onPressed: () {
                  Navigator.pushNamed(context, '/signup'); // 회원가입 페이지로 이동
                },
                child: Text('회원가입'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
